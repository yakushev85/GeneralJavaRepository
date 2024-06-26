import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService, User } from '@auth0/auth0-angular';
import { Article } from 'src/app/core/models/article.model';
import { Comment } from 'src/app/core/models/comment.model';
import { CommentService } from 'src/app/core/services/comment.service';

@Component({
  selector: 'app-article-item',
  templateUrl: './article-item.component.html',
  styleUrls: ['./article-item.component.css']
})
export class ArticleItemComponent implements OnInit {
  isSubmitting = false;
  commentForm = this.fb.group({content: ['', Validators.required]});
  currentUser: User | undefined;
  article: Article | undefined;
  comments: Comment[] = [];
  pageNumber = 1;
  pageSize = 10;
  totalElements = 0;

  constructor(
    private route: ActivatedRoute,
    private commentService: CommentService,
    public auth: AuthService,
    private fb: FormBuilder) {
      this.route.data.subscribe(
        (data) => {
          this.article = (data as {article: Article}).article;
          this.loadComments();
        }
      );

      this.auth.user$.subscribe((value) => {
        if (value) {
          this.currentUser = value;
        }
      });
    }

  loadComments() {
    if (!this.article?.id) {
      return;
    }
    
    this.commentService.getAllbyArticleId(this.pageNumber - 1, this.pageSize, this.article?.id).subscribe((data) => {
      if (data) {
        this.comments = (data.content as Comment[]);

        this.pageNumber = data.pageable.pageNumber + 1;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
      }
    });
  }

  updatePageSize(value: string) {
    this.pageSize = +value;
    this.loadComments();
  }

  ngOnInit(): void {
  }


  submitForm() {
    let newComment = {
      content: this.commentForm.value.content,
      articleId: this.article?.id,
      authorEmail: this.currentUser?.email,
      authorName: this.currentUser?.nickname
    }
    this.commentService.add(newComment).subscribe(
      () => {
        this.loadComments();
        this.commentForm.reset();
      }
    );
  }

}
