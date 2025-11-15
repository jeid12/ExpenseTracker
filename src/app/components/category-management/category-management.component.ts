import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService, Category } from '../../services/admin/admin.service';

@Component({
  selector: 'app-category-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-management.component.html',
  styleUrl: './category-management.component.scss'
})
export class CategoryManagementComponent implements OnInit {
  categories: Category[] = [];
  loading = false;
  error = '';
  showModal = false;
  editMode = false;
  currentCategory: Category = this.getEmptyCategory();

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.loading = true;
    this.adminService.getAllCategories().subscribe({
      next: (data) => {
        this.categories = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load categories';
        this.loading = false;
        console.error('Error loading categories:', error);
      }
    });
  }

  openCreateModal(): void {
    this.editMode = false;
    this.currentCategory = this.getEmptyCategory();
    this.showModal = true;
  }

  openEditModal(category: Category): void {
    this.editMode = true;
    this.currentCategory = { ...category };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.currentCategory = this.getEmptyCategory();
  }

  saveCategory(): void {
    if (!this.currentCategory.name.trim()) {
      this.error = 'Category name is required';
      return;
    }

    this.loading = true;
    const operation = this.editMode
      ? this.adminService.updateCategory(this.currentCategory.id!, this.currentCategory)
      : this.adminService.createCategory(this.currentCategory);

    operation.subscribe({
      next: () => {
        this.loadCategories();
        this.closeModal();
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to save category';
        this.loading = false;
        console.error('Error saving category:', error);
      }
    });
  }

  toggleStatus(category: Category): void {
    this.adminService.toggleCategoryStatus(category.id!).subscribe({
      next: () => {
        this.loadCategories();
      },
      error: (error) => {
        this.error = 'Failed to update category status';
        console.error('Error updating status:', error);
      }
    });
  }

  deleteCategory(category: Category): void {
    if (confirm(`Are you sure you want to delete "${category.name}"?`)) {
      this.adminService.deleteCategory(category.id!).subscribe({
        next: () => {
          this.loadCategories();
        },
        error: (error) => {
          this.error = 'Failed to delete category';
          console.error('Error deleting category:', error);
        }
      });
    }
  }

  getEmptyCategory(): Category {
    return {
      name: '',
      type: 'EXPENSE',
      description: '',
      isActive: true
    };
  }
}
